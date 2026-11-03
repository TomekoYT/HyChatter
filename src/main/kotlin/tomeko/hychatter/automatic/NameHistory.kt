package tomeko.hychatter.automatic

//? if 1.8.9 {
/*import net.minecraft.client.Minecraft
*///?} else {
import net.fabricmc.loader.api.FabricLoader
//?}
import com.google.gson.JsonParser
import tomeko.hychatter.utils.Debug
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object NameHistory {
    private class Entry(@Volatile var name: String, @Volatile var occurrences: Int)

    private val entries = ConcurrentHashMap<String, Entry>()
    private val worker = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "HyChatter-NameHistory").apply { isDaemon = true }
    }

    private val file: File by lazy {
        val configDir: File =
            //? if 1.8.9
            //File(Minecraft.getMinecraft().mcDataDir, "config")
            //? else
            FabricLoader.getInstance().configDir.toFile()
        File(configDir, "hychatter/name_history.txt")
    }

    fun register() {
        load()
        Runtime.getRuntime().addShutdownHook(Thread { save() })
    }

    fun names(): List<String> = entries.values.map { it.name }

    fun capture(command: String) {
        for (name in AutocompleteCore.extractUsernames(command)) remember(name)
    }

    private fun remember(name: String) {
        val key = name.lowercase()
        val existing = entries[key]
        if (existing != null) {
            existing.occurrences++
            return
        }

        val entry = Entry(name, 1)
        if (entries.putIfAbsent(key, entry) != null) return
        worker.execute { save() }

        worker.execute {
            val properName = fetchProperName(name) ?: return@execute
            if (properName != entry.name && entries[key] === entry) {
                entry.name = properName
                save()
            }
        }
    }

    fun remove(name: String): Boolean {
        if (entries.remove(name.lowercase()) == null) return false
        worker.execute { save() }
        return true
    }

    private fun fetchProperName(name: String): String? {
        var connection: HttpURLConnection? = null
        return try {
            connection = URL("https://api.mojang.com/users/profiles/minecraft/$name").openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.useCaches = false
            if (connection.responseCode != HttpURLConnection.HTTP_OK) return null
            val body = connection.inputStream.use { String(it.readBytes(), StandardCharsets.UTF_8) }
            JsonParser().parse(body).asJsonObject.get("name").asString.takeIf(AutocompleteCore::isValidUsername)
        } catch (e: Exception) {
            Debug.log("Failed to look up the capitalization of $name: $e")
            null
        } finally {
            connection?.disconnect()
        }
    }

    private fun load() {
        try {
            if (!file.exists()) return
            file.readLines(StandardCharsets.UTF_8).forEach { line ->
                val parts = line.trim().split(" ")
                val count = parts.getOrNull(1)?.toIntOrNull()
                if (parts.size == 2 && count != null && AutocompleteCore.isValidUsername(parts[0])) {
                    entries[parts[0].lowercase()] = Entry(parts[0], count)
                } else if (line.isNotBlank()) {
                    Debug.log("Malformed line in name history: $line")
                }
            }
        } catch (e: Exception) {
            Debug.forceLog("Failed to load the name history: $e")
        }
    }

    @Synchronized
    private fun save() {
        try {
            file.parentFile.mkdirs()
            val temp = File(file.parentFile, "name_history.txt.tmp")
            temp.writeText(entries.values.joinToString("") { "${it.name} ${it.occurrences}\n" }, StandardCharsets.UTF_8)
            try {
                Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
            } catch (_: Exception) {
                Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: Exception) {
            Debug.forceLog("Failed to save the name history: $e")
        }
    }
}
