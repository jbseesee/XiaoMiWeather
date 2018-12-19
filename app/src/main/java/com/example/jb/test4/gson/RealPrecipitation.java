package com.example.jb.test4.gson;

import com.google.gson.annotations.SerializedName;

public class RealPrecipitation {
    @SerializedName("nearest")
    private RealNearest nearest;
    @SerializedName("local")
    private RealLocal local;
    public void setNearest(RealNearest nearest) {
         this.nearest = nearest;
     }
     public RealNearest getNearest() {
         return nearest;
     }

    public void setLocal(RealLocal local) {
         this.local = local;
     }
     public RealLocal getLocal() {
         return local;
     }

}