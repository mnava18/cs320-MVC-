package cs320.Homework2;

import java.util.ArrayList;
import java.util.List;


public class NotesBean {

	  List<Notes> entries;

	    public NotesBean()
	    
	    {
	        entries = new ArrayList<Notes>();
	    }

	    public void setAddEntry( String dummy )
	    {
	        Notes entry = new Notes(0, dummy, dummy, dummy);
	        entries.add( entry );
	    }

	    public Notes getLastEntry()
	    {
	        return entries.get( entries.size() - 1 );
	    }

	    public List<Notes> getEntries()
	    {
	        return entries;
	    }

	    public void setEntries( List<Notes> entries )
	    {
	        this.entries = entries;
	    }

	}