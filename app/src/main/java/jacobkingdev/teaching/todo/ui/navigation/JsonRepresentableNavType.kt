package jacobkingdev.teaching.todo.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

class JsonRepresentableNavType<T: Any>(
    private val serialiser: KSerializer<T>
): NavType<T>(isNullableAllowed = true) {

    override fun get(bundle: Bundle, key: String): T? {
        val urlSafeJson = bundle.getString(key)
        val urlUnsafeJson = urlSafeJson?.let { URLDecoder.decode(it, Charsets.UTF_8.name()) }
        return urlUnsafeJson?.let { Json.decodeFromString(serialiser, it) }
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        val urlUnsafeJson = Json.encodeToString(serialiser, value)
        val urlSafeJson = URLEncoder.encode(urlUnsafeJson, Charsets.UTF_8.name())
        bundle.putString(key, urlSafeJson)
    }

    override fun parseValue(value: String): T {
        val urlUnsafeJson = URLDecoder.decode(value, Charsets.UTF_8.name())
        return Json.decodeFromString(serialiser, urlUnsafeJson)
    }

    override fun serializeAsValue(value: T): String {
        val urlUnsafeJson = Json.encodeToString(serialiser, value)
        return URLEncoder.encode(urlUnsafeJson, Charsets.UTF_8.name())
    }
}