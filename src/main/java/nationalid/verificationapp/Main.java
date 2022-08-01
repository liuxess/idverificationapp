package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;

import nationalid.SegmentedNationalID;
import nationalid.loggers.ConsoleLogger;
import nationalid.loggers.FileLogger;
import nationalid.loggers.LogManager;
import nationalid.verificationapp.Filters.FilterManager;
import nationalid.verificationapp.Filters.Filterable;
import nationalid.verificationapp.Filters.GenderFilter;
import nationalid.verificationapp.IO.Arguments;
import nationalid.verificationapp.IO.InputManager;
import nationalid.verificationapp.IO.OutputManager;
import nationalid.verificationapp.Sorters.BirthDateSorter;
import nationalid.verificationapp.Sorters.Sortable;
import nationalid.verificationapp.Sorters.SortingManager;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        LogManager logManager = new LogManager(false, new ConsoleLogger(), new FileLogger());

        if (arguments.inputFileName == null)
            throw new RuntimeException("File name is mandatory. Pass the input file name using -i or --input flags.");

        InputManager inputManager = new InputManager(arguments.inputFileName);
        List<SegmentedNationalID> nationalIDs = inputManager
                .ReadIDsFromFile(logManager);

        List<Filterable> filters = new ArrayList<>();
        if (arguments.filter != null)
            filters.add(new GenderFilter(arguments.filter));
        FilterManager filterManager = new FilterManager(filters);
        filterManager.ApplyFilters(nationalIDs);

        IDVerificator verificator = new IDVerificator();
        CategorizedIDLists categorizedList = verificator.VerifyIDs(nationalIDs);

        List<Sortable> sorters = new ArrayList<>();
        sorters.add(new BirthDateSorter(arguments.sort));
        SortingManager sortingManager = new SortingManager(sorters);

        sortingManager.ApplySort(categorizedList.getCorrect());

        OutputManager outputManager = new OutputManager(logManager);
        outputManager.OutputMessage(categorizedList);
    }

}
