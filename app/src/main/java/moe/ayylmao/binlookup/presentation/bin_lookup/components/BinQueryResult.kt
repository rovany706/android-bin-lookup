package moe.ayylmao.binlookup.presentation.bin_lookup.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import moe.ayylmao.binlookup.R
import moe.ayylmao.binlookup.domain.model.binlist.BinlistResponse

enum class UriTypes(val uriPrefix: String) {
    URL("https://"),
    TEL("tel:"),
    GEO("geo:")
}

fun toUri(string: String, uriType: UriTypes): String {
    return "${uriType.uriPrefix}$string"
}

@Composable
fun AddLink(builder: AnnotatedString.Builder, uri: String, linkText: String) {
    builder.pushStringAnnotation(tag = "URI", annotation = uri)
    builder.withStyle(
        style = SpanStyle(
            color = MaterialTheme.colors.secondary, textDecoration = TextDecoration.Underline
        )
    ) {
        append(linkText)
    }
    builder.pop()
}

@Composable
fun BinQueryResult(
    modifier: Modifier, binlistResponse: BinlistResponse
) {
    val annotatedString = buildAnnotatedString {
        if (binlistResponse.bank == null) {
            append(stringResource(R.string.no_bank_info))
        } else {
            val bank = binlistResponse.bank
            withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
                if (bank.name != null) {
                    append(String.format(stringResource(R.string.bank_name), bank.name))
                }
                if (bank.url != null) {
                    append('\n')
                    append(stringResource(R.string.url))
                    AddLink(
                        builder = this,
                        uri = toUri(bank.url, UriTypes.URL),
                        linkText = bank.url
                    )
                }
                if (bank.phone != null) {
                    append('\n')
                    append(stringResource(R.string.telephone))
                    AddLink(
                        builder = this,
                        uri = toUri(bank.phone, UriTypes.TEL),
                        linkText = bank.phone
                    )
                }

                if (binlistResponse.country?.latitude != null && binlistResponse.country.longitude != null) {
                    append('\n')
                    append(stringResource(R.string.location))
                    AddLink(
                        builder = this,
                        uri = toUri(
                            "${binlistResponse.country.latitude},${binlistResponse.country.longitude}",
                            UriTypes.GEO
                        ),
                        linkText = bank.city ?: binlistResponse.country.name
                        ?: stringResource(R.string.on_map)
                    )
                }
            }
        }
    }

    val uriHandler = LocalUriHandler.current

    Card(modifier = modifier, elevation = 5.dp) {
        ClickableText(modifier = Modifier.padding(15.dp),
            text = annotatedString,
            style = MaterialTheme.typography.body1,
            onClick = { offset ->
                annotatedString.getStringAnnotations("URI", start = offset, end = offset)
                    .firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
            })
    }
}
