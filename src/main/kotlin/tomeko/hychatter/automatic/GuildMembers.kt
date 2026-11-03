package tomeko.hychatter.automatic

import net.minecraft.client.Minecraft
import tomeko.hychatter.utils.Constants
import tomeko.hychatter.utils.Debug
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.concurrent.thread

object GuildMembers {
    private const val REFRESH_INTERVAL_MS = 5 * 60 * 1000L

    @Volatile private var members: List<String> = emptyList()
    @Volatile private var lastRefresh = 0L
    @Volatile private var refreshing = false

    fun members(): List<String> = members

    @Synchronized
    fun refresh() {
        val now = System.currentTimeMillis()
        if (refreshing || now - lastRefresh < REFRESH_INTERVAL_MS) return
        refreshing = true
        lastRefresh = now

        thread(name = "HyChatter-GuildMembers", isDaemon = true) {
            try {
                members = fetch().sortedWith(String.CASE_INSENSITIVE_ORDER)
                Debug.log("Fetched ${members.size} guild members")
            } finally {
                refreshing = false
            }
        }
    }

    private fun fetch(): Set<String> {
        var connection: HttpURLConnection? = null
        return try {
            val uuid =
                //? if 1.8.9
                //Minecraft.getMinecraft().session.profile.id
                //? else
                Minecraft.getInstance().gameProfile.id()
            connection = URL("https://api.sk1er.club/autocomplete/$uuid").openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.useCaches = false
            connection.setRequestProperty("User-Agent", "${Constants.MOD_NAME} ${Constants.MOD_VERSION}")
            if (connection.responseCode != HttpURLConnection.HTTP_OK) return emptySet()
            AutocompleteCore.parseMembers(connection.inputStream.use { String(it.readBytes(), StandardCharsets.UTF_8) })
        } catch (e: Exception) {
            Debug.log("Failed to fetch guild members: $e")
            emptySet()
        } finally {
            connection?.disconnect()
        }
    }
}
