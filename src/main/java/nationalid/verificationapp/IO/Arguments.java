package nationalid.verificationapp.IO;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class Arguments {

    @Parameter
    public List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-i", "--input" }, description = "Input file name/path")
    public String inputFileName;

    @Parameter(names = { "-f", "--filter" }, description = "Filtering whether it should be male of female")
    public String filter;

    @Parameter(names = { "-sbd",
            "--sort-by-birth-date" }, description = "Whether it should be sorted by ascneding or descending birth dates")
    public String sort;
}
