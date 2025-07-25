package fr.acth2.ror.utils.subscribers.gen.utils.parser;

import com.google.gson.Gson;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StructureParser {
    private static final Gson GSON = new Gson();
    public static final Map<ResourceLocation, StructureDefinition> CACHE = new HashMap<>();

    public static StructureDefinition parse(IResourceManager manager, ResourceLocation location) throws MissingStructureException {
        return CACHE.computeIfAbsent(location, loc -> {
            ResourceLocation fileLoc = getStructureLocation(loc);
            try {
                if (!manager.hasResource(fileLoc)) {
                    throw new MissingStructureException("Structure file not found: " + fileLoc);
                }

                try (IResource resource = manager.getResource(fileLoc)) {
                    return GSON.fromJson(new InputStreamReader(resource.getInputStream()), StructureDefinition.class);
                }
            } catch (MissingStructureException e) {
                throw e;
            } catch (Exception e) {
                System.err.println(("Failed to parse structure: " + e + " fileLoc: " + fileLoc));
                throw new MissingStructureException("Failed to parse structure: " + fileLoc, e);
            }
        });
    }

    private static ResourceLocation getStructureLocation(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), "structures/" + loc.getPath() + ".rorstruc");
    }

    public static class MissingStructureException extends RuntimeException {
        public MissingStructureException(String message) {
            super(message);
        }

        public MissingStructureException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class StructureDefinition {
        public BlockEntry[] blocks;

        public static class BlockEntry {
            public String block;
            public String shape; // "cylinder", "box", etc.
            public int radius;
            public int height;
            public LayerEntry layers;
        }

        public static class LayerEntry {
            public String top;
            public String bottom;
        }
    }
}