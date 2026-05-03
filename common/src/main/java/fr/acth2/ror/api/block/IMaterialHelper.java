package fr.acth2.ror.api.block;


public interface IMaterialHelper {
    Object wood();
    Object stone();
    Object metal();
    Object dirt();
    Object sand();
    Object glass();
    Object wool();
    Object leaves();
    Object ice();
    Object water();
    Object of(float hardness, float resistance);
}