package dev.bluetree242.saaiaddons.aistudio;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <T> List<List<T>> splitList(List<T> originalList, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < originalList.size(); i += chunkSize) {
            int end = Math.min(originalList.size(), i + chunkSize);
            chunks.add(new ArrayList<>(originalList.subList(i, end)));
        }
        return chunks;
    }
}
