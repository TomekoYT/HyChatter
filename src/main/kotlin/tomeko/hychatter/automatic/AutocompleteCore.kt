package tomeko.hychatter.automatic

import com.google.gson.JsonParser

object AutocompleteCore {
    val PARTY_USER_SUBCOMMANDS = listOf("invite", "challenge", "promote", "remove", "kick", "accept", "transfer")

    val PARTY_PLAIN_SUBCOMMANDS = listOf(
        "kickoffline", "leave", "disband", "private", "home", "warp", "list", "mute", "help", "settings", "poll"
    )

    private val PARTY_PREFIXES = listOf("/p ", "/party ")

    val PARTY_COMMANDS: List<String> = PARTY_PREFIXES.flatMap { prefix ->
        (PARTY_USER_SUBCOMMANDS + PARTY_PLAIN_SUBCOMMANDS).map { "$prefix$it " } + prefix
    }

    val WHISPER_COMMANDS = listOf("/msg ", "/tell ", "/w ", "/t ", "/whisper ", "/boop ")

    private val USERNAME = Regex("[A-Za-z0-9_]{1,16}")

    fun isValidUsername(name: String): Boolean = USERNAME.matches(name)

    class Completion(val start: Int, val end: Int, val options: List<String>)

    fun extractUsernames(message: String): List<String> {
        val split = message.trim().split(Regex(" +"))
        if (split.size < 2) return emptyList()

        val command = split[0].lowercase() + " "
        if (PARTY_PREFIXES.contains(command)) {
            val sub = split[1].lowercase()
            if (PARTY_PLAIN_SUBCOMMANDS.contains(sub)) return emptyList()
            val startIndex = if (PARTY_USER_SUBCOMMANDS.contains(sub)) 2 else 1
            return split.drop(startIndex).filter(::isValidUsername)
        }
        if (WHISPER_COMMANDS.contains(command)) {
            return listOf(split[1]).filter(::isValidUsername)
        }
        return emptyList()
    }

    private fun matchingCommand(beforeCursor: String): String? {
        val lowercase = beforeCursor.lowercase()
        return PARTY_COMMANDS.firstOrNull { lowercase.startsWith(it) }
            ?: WHISPER_COMMANDS.firstOrNull { lowercase.startsWith(it) }
    }

    fun complete(input: String, cursor: Int, names: Collection<String>): Completion? {
        val before = input.substring(0, cursor.coerceIn(0, input.length))
        val command = matchingCommand(before) ?: return null
        val isWhisper = WHISPER_COMMANDS.contains(command)
        val start = before.lastIndexOf(' ') + 1
        val end = before.length

        if (isWhisper && before.substring(command.length, start).isNotBlank()) return Completion(start, end, emptyList())

        val subcommand = command.trim().split(' ').getOrNull(1) ?: ""
        if (PARTY_PLAIN_SUBCOMMANDS.contains(subcommand)) return null

        val candidates = LinkedHashMap<String, String>()
        for (name in names) candidates.putIfAbsent(name.lowercase(), name)
        if (PARTY_COMMANDS.contains(command) && subcommand.isEmpty()) {
            for (sub in PARTY_USER_SUBCOMMANDS + PARTY_PLAIN_SUBCOMMANDS) candidates.putIfAbsent(sub, sub)
        }
        if (candidates.isEmpty()) return null

        val partial = before.substring(start).lowercase()

        if (isWhisper) {
            if (candidates.containsKey(partial) || candidates.keys.none { it.startsWith(partial) }) {
                return Completion(start, end, emptyList())
            }
        }

        val alreadyMentioned = if (start > command.length) {
            before.substring(command.length, start).trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
                .map { it.lowercase() }.toSet()
        } else emptySet()

        val options = candidates.filterKeys { it.startsWith(partial) && it !in alreadyMentioned }.values
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
        return Completion(start, end, options)
    }

    fun parseMembers(body: String): Set<String> = try {
        val root = JsonParser().parse(body)
        if (!root.isJsonObject) emptySet() else {
            val array = root.asJsonObject.getAsJsonArray("members")
            array?.map { it.asString }?.filter(::isValidUsername)?.toSet() ?: emptySet()
        }
    } catch (_: Exception) {
        emptySet()
    }
}
