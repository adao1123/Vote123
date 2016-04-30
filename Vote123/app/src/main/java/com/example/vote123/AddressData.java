package com.example.vote123;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao1 on 4/30/2016.
 */
public class AddressData {
    @SerializedName("pollingLocations")
    PollingPlace[] pollingLocations;
    @SerializedName("dropOffLocations")
    String[] dropOffLocations;

    public PollingPlace[] getPollingLocations() {
        return pollingLocations;
    }

    public String[] getDropOffLocations() {
        return dropOffLocations;
    }

    public class PollingPlace{
        @SerializedName("id")
        String pollingPlaceID;
        @SerializedName("address")
        Address pollingPlaceAddress;
        @SerializedName("name")
        String pollingPlaceName;
        @SerializedName("pollingHours")
        String pollingPlaceHours;

        public String getPollingPlaceID() {
            return pollingPlaceID;
        }

        public Address getPollingPlaceAddress() {
            return pollingPlaceAddress;
        }

        public String getPollingPlaceName() {
            return pollingPlaceName;
        }

        public String getPollingPlaceHours() {
            return pollingPlaceHours;
        }

        public class Address{
            @SerializedName("locationName")
            String addressName;
            @SerializedName("line1")
            String addressStreet;
            @SerializedName("city")
            String addressCity;
            @SerializedName("state")
            String addressState;
            @SerializedName("zip")
            String addressZip;

            public String getAddressName() {
                return addressName;
            }

            public String getAddressStreet() {
                return addressStreet;
            }

            public String getAddressCity() {
                return addressCity;
            }

            public String getAddressState() {
                return addressState;
            }

            public String getAddressZip() {
                return addressZip;
            }
        }
    }
}
