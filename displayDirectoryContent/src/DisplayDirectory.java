import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisplayDirectory {

    private static int defaultDeep = 0;

    public static void main(String[] args) {
        try {
            displayDirectoryContent("/Users/feb/Documents",  -1);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    private static void displayDirectoryContent(String directoryPath, int maxLevel) throws IOException {
        defaultDeep = directoryPath.split("/").length;
        try (Stream<Path> walk = Files
                .walk(Paths.get(directoryPath),
                        maxLevel < 0 ? Integer.MAX_VALUE : maxLevel,
                        FileVisitOption.FOLLOW_LINKS)) {
                    walk.map(obj -> obj.toString())
                        .collect(Collectors.toList())
                        .forEach(DisplayDirectory::printFileName);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }

    private static void printFileName(String path) {
        var paths = path.split("/");
        var deep = paths.length - defaultDeep;
        File file = new File(path);
        System.out.printf("%s %s - %s \n",
                "\t".repeat(deep),
                paths[paths.length - 1],
                file.isDirectory() ? "dir" : "file");
    }

}
