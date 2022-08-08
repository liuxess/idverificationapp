package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.JCommander;

import nationalid.SegmentedNationalID;
import nationalid.loggers.ConsoleLogger;
import nationalid.loggers.FileLogger;
import nationalid.loggers.LogManager;
import nationalid.models.NationalID;
import nationalid.models.Calculators.GlobalCodeCalculator;
import nationalid.models.Calculators.LithuanianCodeCalculator;
import nationalid.verificationapp.Filters.FilterManager;
import nationalid.verificationapp.Filters.Filterable;
import nationalid.verificationapp.Filters.GenderFilter;
import nationalid.verificationapp.IO.Arguments;
import nationalid.verificationapp.IO.FileInputManager;
import nationalid.verificationapp.IO.OutputManager;
import nationalid.verificationapp.Sorters.BirthDateSorter;
import nationalid.verificationapp.Sorters.SortingManager;

/**
 * To be launched with parameters:
 * <ul>
 * -in : input file name
 * -f : (Optional) filtering by gender male|female
 * -sbd: (Optional, default:asc) sorting by birthdate asc|desc
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        if (arguments.inputFileName == null)
            throw new RuntimeException("File name is mandatory. Pass the input file name using -i or --input flags.");

        GlobalCodeCalculator.setGlobalInstance(new LithuanianCodeCalculator());

        LogManager.getGlobalInstance().addLogger(new ConsoleLogger());
        LogManager.getGlobalInstance().addLogger(new FileLogger());

        FilterManager filterManager = new FilterManager();
        if (arguments.filter != null)
            filterManager.AddFilter(new GenderFilter(arguments.filter));

        SortingManager sortingManager = new SortingManager(new BirthDateSorter(arguments.sort));

        FileInputManager inputManager = new FileInputManager(arguments.inputFileName);
        BatchProcessor batchProcessor = new BatchProcessor(filterManager, sortingManager);

        String[] parsedFile = inputManager.GetContentFromFile();
        List<SegmentedNationalID> segmentedNationalIDs = BatchProcessor
                .ToSegmentedNationalIDs(Arrays.asList(parsedFile));

        batchProcessor.SortAndFilter(segmentedNationalIDs);

        CategorizedIDLists categorizedList = IDVerificator.VerifyIDs(segmentedNationalIDs);

        batchProcessor.SortAndFilter(categorizedList.getCorrect());

        OutputManager outputManager = new OutputManager();
        outputManager.Output(categorizedList);
    }

}
