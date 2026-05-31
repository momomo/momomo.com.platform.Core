/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package momomo.com.sources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Original from: 
 *     org.springframework.web.util.HtmlCharacterEntityReferences
 *     org.springframework.web.util.HtmlCharacterEntityReferences.properties
 * 
 * Content has slightly changed.     
 * 
 * 
 * For singelton usage, see {@link momomo.com.Html#CHARS}
 *
 * @author Joseph S.
 */
public final class HtmlChars {
    
    private final String[] CHARACTER_TO_ENTITY = new String[3000];
    private final Map<String, Character> ENTITY_TO_CHARACTER = new HashMap<>(252);
    
    private static final char REFERENCE_START = '&';
    
    private static final char REFERENCE_END = ';';
    
    public static final char CHAR_NULL = (char) -1;
    
    // Mo: Change here
    private static final String PROPERTIES = "# Character Entity References defined by the HTML 4.0 standard.\n" +
        "# A complete description of the HTML 4.0 character set can be found at:\n" +
        "# http://www.w3.org/TR/html4/charset.html\n" +
        "\n" +
        "\n" +
        "# Character entity references for ISO 8859-1 characters\n" +
        "\n" +
        "160 = nbsp\n" +
        "161 = iexcl\n" +
        "162 = cent\n" +
        "163 = pound\n" +
        "164 = curren\n" +
        "165 = yen\n" +
        "166 = brvbar\n" +
        "167 = sect\n" +
        "168 = uml\n" +
        "169 = copy\n" +
        "170 = ordf\n" +
        "171 = laquo\n" +
        "172 = not\n" +
        "173 = shy\n" +
        "174 = reg\n" +
        "175 = macr\n" +
        "176 = deg\n" +
        "177 = plusmn\n" +
        "178 = sup2\n" +
        "179 = sup3\n" +
        "180 = acute\n" +
        "181 = micro\n" +
        "182 = para\n" +
        "183 = middot\n" +
        "184 = cedil\n" +
        "185 = sup1\n" +
        "186 = ordm\n" +
        "187 = raquo\n" +
        "188 = frac14\n" +
        "189 = frac12\n" +
        "190 = frac34\n" +
        "191 = iquest\n" +
        "192 = Agrave\n" +
        "193 = Aacute\n" +
        "194 = Acirc\n" +
        "195 = Atilde\n" +
        "196 = Auml\n" +
        "197 = Aring\n" +
        "198 = AElig\n" +
        "199 = Ccedil\n" +
        "200 = Egrave\n" +
        "201 = Eacute\n" +
        "202 = Ecirc\n" +
        "203 = Euml\n" +
        "204 = Igrave\n" +
        "205 = Iacute\n" +
        "206 = Icirc\n" +
        "207 = Iuml\n" +
        "208 = ETH\n" +
        "209 = Ntilde\n" +
        "210 = Ograve\n" +
        "211 = Oacute\n" +
        "212 = Ocirc\n" +
        "213 = Otilde\n" +
        "214 = Ouml\n" +
        "215 = times\n" +
        "216 = Oslash\n" +
        "217 = Ugrave\n" +
        "218 = Uacute\n" +
        "219 = Ucirc\n" +
        "220 = Uuml\n" +
        "221 = Yacute\n" +
        "222 = THORN\n" +
        "223 = szlig\n" +
        "224 = agrave\n" +
        "225 = aacute\n" +
        "226 = acirc\n" +
        "227 = atilde\n" +
        "228 = auml\n" +
        "229 = aring\n" +
        "230 = aelig\n" +
        "231 = ccedil\n" +
        "232 = egrave\n" +
        "233 = eacute\n" +
        "234 = ecirc\n" +
        "235 = euml\n" +
        "236 = igrave\n" +
        "237 = iacute\n" +
        "238 = icirc\n" +
        "239 = iuml\n" +
        "240 = eth\n" +
        "241 = ntilde\n" +
        "242 = ograve\n" +
        "243 = oacute\n" +
        "244 = ocirc\n" +
        "245 = otilde\n" +
        "246 = ouml\n" +
        "247 = divide\n" +
        "248 = oslash\n" +
        "249 = ugrave\n" +
        "250 = uacute\n" +
        "251 = ucirc\n" +
        "252 = uuml\n" +
        "253 = yacute\n" +
        "254 = thorn\n" +
        "255 = yuml\n" +
        "\n" +
        "\n" +
        "# Character entity references for symbols, mathematical symbols, and Greek letters\n" +
        "\n" +
        "402 = fnof\n" +
        "913 = Alpha\n" +
        "914 = Beta\n" +
        "915 = Gamma\n" +
        "916 = Delta\n" +
        "917 = Epsilon\n" +
        "918 = Zeta\n" +
        "919 = Eta\n" +
        "920 = Theta\n" +
        "921 = Iota\n" +
        "922 = Kappa\n" +
        "923 = Lambda\n" +
        "924 = Mu\n" +
        "925 = Nu\n" +
        "926 = Xi\n" +
        "927 = Omicron\n" +
        "928 = Pi\n" +
        "929 = Rho\n" +
        "931 = Sigma\n" +
        "932 = Tau\n" +
        "933 = Upsilon\n" +
        "934 = Phi\n" +
        "935 = Chi\n" +
        "936 = Psi\n" +
        "937 = Omega\n" +
        "945 = alpha\n" +
        "946 = beta\n" +
        "947 = gamma\n" +
        "948 = delta\n" +
        "949 = epsilon\n" +
        "950 = zeta\n" +
        "951 = eta\n" +
        "952 = theta\n" +
        "953 = iota\n" +
        "954 = kappa\n" +
        "955 = lambda\n" +
        "956 = mu\n" +
        "957 = nu\n" +
        "958 = xi\n" +
        "959 = omicron\n" +
        "960 = pi\n" +
        "961 = rho\n" +
        "962 = sigmaf\n" +
        "963 = sigma\n" +
        "964 = tau\n" +
        "965 = upsilon\n" +
        "966 = phi\n" +
        "967 = chi\n" +
        "968 = psi\n" +
        "969 = omega\n" +
        "977 = thetasym\n" +
        "978 = upsih\n" +
        "982 = piv\n" +
        "8226 = bull\n" +
        "8230 = hellip\n" +
        "8242 = prime\n" +
        "8243 = Prime\n" +
        "8254 = oline\n" +
        "8260 = frasl\n" +
        "8472 = weierp\n" +
        "8465 = image\n" +
        "8476 = real\n" +
        "8482 = trade\n" +
        "8501 = alefsym\n" +
        "8592 = larr\n" +
        "8593 = uarr\n" +
        "8594 = rarr\n" +
        "8595 = darr\n" +
        "8596 = harr\n" +
        "8629 = crarr\n" +
        "8656 = lArr\n" +
        "8657 = uArr\n" +
        "8658 = rArr\n" +
        "8659 = dArr\n" +
        "8660 = hArr\n" +
        "8704 = forall\n" +
        "8706 = part\n" +
        "8707 = exist\n" +
        "8709 = empty\n" +
        "8711 = nabla\n" +
        "8712 = isin\n" +
        "8713 = notin\n" +
        "8715 = ni\n" +
        "8719 = prod\n" +
        "8721 = sum\n" +
        "8722 = minus\n" +
        "8727 = lowast\n" +
        "8730 = radic\n" +
        "8733 = prop\n" +
        "8734 = infin\n" +
        "8736 = ang\n" +
        "8743 = and\n" +
        "8744 = or\n" +
        "8745 = cap\n" +
        "8746 = cup\n" +
        "8747 = int\n" +
        "8756 = there4\n" +
        "8764 = sim\n" +
        "8773 = cong\n" +
        "8776 = asymp\n" +
        "8800 = ne\n" +
        "8801 = equiv\n" +
        "8804 = le\n" +
        "8805 = ge\n" +
        "8834 = sub\n" +
        "8835 = sup\n" +
        "8836 = nsub\n" +
        "8838 = sube\n" +
        "8839 = supe\n" +
        "8853 = oplus\n" +
        "8855 = otimes\n" +
        "8869 = perp\n" +
        "8901 = sdot\n" +
        "8968 = lceil\n" +
        "8969 = rceil\n" +
        "8970 = lfloor\n" +
        "8971 = rfloor\n" +
        "9001 = lang\n" +
        "9002 = rang\n" +
        "9674 = loz\n" +
        "9824 = spades\n" +
        "9827 = clubs\n" +
        "9829 = hearts\n" +
        "9830 = diams\n" +
        "\n" +
        "\n" +
        "# Character entity references for markup-significant and internationalization characters\n" +
        "\n" +
        "34 = quot\n" +
        "38 = amp\n" +
        "60 = lt\n" +
        "62 = gt\n" +
        "338 = OElig\n" +
        "339 = oelig\n" +
        "352 = Scaron\n" +
        "353 = scaron\n" +
        "376 = Yuml\n" +
        "710 = circ\n" +
        "732 = tilde\n" +
        "8194 = ensp\n" +
        "8195 = emsp\n" +
        "8201 = thinsp\n" +
        "8204 = zwnj\n" +
        "8205 = zwj\n" +
        "8206 = lrm\n" +
        "8207 = rlm\n" +
        "8211 = ndash\n" +
        "8212 = mdash\n" +
        "8216 = lsquo\n" +
        "8217 = rsquo\n" +
        "8218 = sbquo\n" +
        "8220 = ldquo\n" +
        "8221 = rdquo\n" +
        "8222 = bdquo\n" +
        "8224 = dagger\n" +
        "8225 = Dagger\n" +
        "8240 = permil\n" +
        "8249 = lsaquo\n" +
        "8250 = rsaquo\n" +
        "8364 = euro\n";
    
    /**
     * Returns a new set of character entity references reflecting the HTML 4.0 character set.
     */
    public HtmlChars() {
        Properties entityReferences = new Properties();
        
        // Mo: Change here
        InputStream is = new ByteArrayInputStream(PROPERTIES.getBytes(StandardCharsets.UTF_8));
        
        if (is == null) {
            throw new IllegalStateException(
                "Cannot find reference definition file [HtmlCharacterEntityReferences.properties] as class path resource");
        }
        try {
            try {
                entityReferences.load(is);
            } finally {
                is.close();
            }
        } catch (IOException ex) {
            throw new IllegalStateException(
                "Failed to parse reference definition file [HtmlCharacterEntityReferences.properties]: " + ex.getMessage());
        }
        
        // Parse reference definition properties
        Enumeration<?> keys = entityReferences.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            int referredChar = Integer.parseInt(key);
            
            assert referredChar < 1000 || (referredChar >= 8000 && referredChar < 10000);
            
            int index = (referredChar < 1000 ? referredChar : referredChar - 7000);
            String reference = entityReferences.getProperty(key);
            this.CHARACTER_TO_ENTITY[index] = REFERENCE_START + reference + REFERENCE_END;
            this.ENTITY_TO_CHARACTER.put(reference, (char) referredChar);
        }
    }
    
    /**
     * Return the reference mapped to the given character or {@code null}.
     */
    public String convertToReference(char character) {
        if (character < 1000 || (character >= 8000 && character < 10000)) {
            int index = (character < 1000 ? character : character - 7000);
            
            return this.CHARACTER_TO_ENTITY[index];
        }
        return null;
    }
    
    /**
     * Return the char mapped to the given entityReference or -1.
     */
    public char convertToCharacter(String entityReference) {
        Character referredCharacter = this.ENTITY_TO_CHARACTER.get(entityReference);
        return Objects.requireNonNullElse(referredCharacter, CHAR_NULL);
    }
}
