package nationalid.verificationapp.IO;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.beust.jcommander.JCommander;

public class ArgumentsTest {

    @Test
    public void TestArgumentGeneration() {

        Arguments args = new Arguments();
        String[] passedArguments = { "-i", "text.txt", "-f", "male", "-sbd", "asc",
                "unit" };
        JCommander.newBuilder().addObject(args).build().parse(passedArguments);

        assertTrue("Input file parsed", args.inputFileName.equals("text.txt"));
        assertTrue("Gender filter parsed", args.filter.equals("male"));
        assertTrue("Sorting order parsed", args.sort.equals("asc"));
    }

}
