package fr.acth2.ror.api.tileentity;


import java.util.function.Supplier;

public interface ITileEntityHelper {
    Object create(Supplier<?> factory, Object... blocks);
}