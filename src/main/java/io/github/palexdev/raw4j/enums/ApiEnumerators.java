/*
 * Copyright (C) 2021 Parisi Alessandro
 * This file is part of RAW4J (https://github.com/palexdev/RAW4J).
 *
 * RAW4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RAW4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RAW4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.raw4j.enums;

import com.google.gson.annotations.SerializedName;

/**
 * This class contains various enumerators needed for APIs
 */
public class ApiEnumerators {

    private ApiEnumerators() {}

    public enum AcceptPMsEnum {
        @SerializedName("everyone")
        EVERYONE,

        @SerializedName("whitelisted")
        WHITELISTED;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum CountryCode {
        WF, JP, JM, JO, WS, JE, GW, GU, GT, GS, GR, GQ, GP, GY, GG, GF, GE, GD, GB, GA,
        GN, GM, GL, GI, GH, PR, PS, PW, PT, PY, PA, PF, PG, PE, PK, PH, PN, PL, PM, ZM,
        ZA, ZZ, ZW, ME, MD, MG, MF, MA, MC, MM, ML, MO, MN, MH, MK, MU, MT, MW, MV, MQ,
        MP, MS, MR, MY, MX, MZ, FR, FI, FJ, FK, FM, FO, CK, CI, CH, CO, CN, CM, CL, CC,
        CA, CG, CF, CD, CZ, CY, CX, CR, CW, CV, CU, SZ, SY, SX, SS, SR, SV, ST, SK, SJ,
        SI, SH, SO, SN, SM, SL, SC, SB, SA, SG, SE, SD, YE, YT, LB, LC, LA, LK, LI, LV,
        LT, LU, LR, LS, LY, VA, VC, VE, VG, IQ, VI, IS, IR, IT, VN, IM, IL, IO, IN, IE,
        ID, BD, BE, BF, BG, BA, BB, BL, BM, BN, BO, BH, BI, BJ, BT, BV, BW, BQ, BR, BS,
        BY, BZ, RU, RW, RS, RE, RO, OM, HR, HT, HU, HK, HN, HM, EH, EE, EG, EC, ET, ES,
        ER, UY, UZ, US, UM, UG, UA, VU, NI, NL, NO, NA, NC, NE, NF, NG, NZ, NP, NR, NU,
        XZ, XX, KG, KE, KI, KH, KN, KM, KR, KP, KW, KZ, KY, DO, DM, DJ, DK, DE, DZ, TZ,
        TV, TW, TT, TR, TN, TO, TL, TM, TJ, TK, TH, TF, TG, TD, TC, AE, AD, AG, AF, AI,
        AM, AL, AO, AN, AQ, AS, AR, AU, AT, AW, AX, AZ, QA
    }

    public enum CommentSort {
        @SerializedName("confidence")
        CONFIDENCE,

        @SerializedName("top")
        TOP,

        @SerializedName("new")
        NEW,

        @SerializedName("controversial")
        CONTROVERSIAL,

        @SerializedName("old")
        OLD,

        @SerializedName("random")
        RANDOM,

        @SerializedName("qa")
        QA,

        @SerializedName("live")
        LIVE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum GeoPopular {
        GLOBAL, US, AR, AU, BG, CA, CL, CO, HR, CZ, FI, FR, DE, GR, HU, IS, IN, IE, IT, JP,
        MY, MX, NZ, PH, PL, PT, PR, RO, RS, SG, ES, SE, TW, TH, TR, GB, US_WA, US_DE, US_DC,
        US_WI, US_WV, US_HI, US_FL, US_WY, US_NH, US_NJ, US_NM, US_TX, US_LA, US_NC, US_ND, US_NE, US_TN,
        US_NY, US_PA, US_CA, US_NV, US_VA, US_CO, US_AK, US_AL, US_AR, US_VT, US_IL, US_GA, US_IN, US_IA,
        US_OK, US_AZ, US_ID, US_CT, US_ME, US_MD, US_MA, US_OH, US_UT, US_MO, US_MN, US_MI, US_RI, US_KS,
        US_MT, US_MS, US_SC, US_KY, US_OR, US_SD
    }

    public enum Media {

        @SerializedName("on")
        ON,

        @SerializedName("off")
        OFF,

        @SerializedName("subreddit")
        SUBREDDIT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
