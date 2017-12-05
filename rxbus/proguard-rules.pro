-keep @com.china.rxbus.MySubscribe class * {*;}
-keep class * {
     @com.china.rxbus.MySubscribe <fields>;
 }
-keep class com.china.rxbus.**{*;}

-keepclassmembers class ** {
@com.china.rxbus.MySubscribe ;
}