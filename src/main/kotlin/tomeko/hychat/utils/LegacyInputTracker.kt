package tomeko.hychat.utils

//? if 1.8.9 {
/*object LegacyInputTracker {
    @Volatile var latestInputTime: Long = System.currentTimeMillis()
        private set

    fun markInput() {
        latestInputTime = System.currentTimeMillis()
    }
}
*///?}