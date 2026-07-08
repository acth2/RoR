package fr.acth2.ror.api.block;

import fr.acth2.ror.api.Services;

public class Props {
    public static Object wood()   { return Services.MATERIALS.wood(); }
    public static Object stone()  { return Services.MATERIALS.stone(); }
    public static Object metal()  { return Services.MATERIALS.metal(); }
    public static Object dirt()   { return Services.MATERIALS.dirt(); }
    public static Object sand()   { return Services.MATERIALS.sand(); }
    public static Object glass()  { return Services.MATERIALS.glass(); }
    public static Object wool()   { return Services.MATERIALS.wool(); }
    public static Object leaves() { return Services.MATERIALS.leaves(); }
    public static Object ice()    { return Services.MATERIALS.ice(); }
    public static Object water()  { return Services.MATERIALS.water(); }
    public static Object of(float hardness, float resistance) {
        return Services.MATERIALS.of(hardness, resistance);
    }
}