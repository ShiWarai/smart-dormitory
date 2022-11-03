/*
package ru.rtulab.smartdormitory.presentation.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object UserClaimParser {
	fun parse(payload: String?): List<Any> {
		if (payload == null) return emptyList()

		// Sometimes "itlab" field is not an Array of Strings, but just String, so this check is needed
		val claims =
			if (!payload.contains("\"itlab\":")) "[]"
			else if (payload.substringAfter("\"itlab\":").startsWith('\"'))
				"[${payload.substringAfter("\"itlab\":").substringBefore(',')}]" // Obtaining single parameter as a list
			else
				"${payload.substringAfter("\"itlab\":").substringBefore(']')}]" // Obtaining a list of claims

		val roles =
			if (!payload.contains("\"role\":")) "[]"
			else if (payload.substringAfter("\"role\":").startsWith('\"'))
				"[${payload.substringAfter("\"role\":").substringBefore(',')}]"
			else
				"${payload.substringAfter("\"role\":").substringBefore(']')}]"
		return Json.decodeFromString<List<String>>(claims).mapNotNull {
			UserClaimCategories.obtainClaimFrom(it)
		} + Json.decodeFromString<List<String>>(roles).mapNotNull {
			UserClaimCategories.obtainClaimFrom(it)
		}
	}
}*/
