package com.example.vote123;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adao1 on 4/30/2016.
 */
public interface CivicInfoApiService {
    @GET("voterinfo")
//    Call<AddressInfo> getAddressInfo(@Query("address") String inputAddress,@Query("key") String apiKey);
    Call<AddressData> getAddressInfo(@Query("address") String inputAddress,@Query("key") String apiKey,@Query("electionId") String id);
}

