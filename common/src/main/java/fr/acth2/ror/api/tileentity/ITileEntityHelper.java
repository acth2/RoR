package fr.acth2.ror.api.BlockEntity;


import java.util.function.Supplier;

public interface ITileEntityHelper {
    Object create(Supplier<?> factory, Object... blocks);
}