import java.io.IOException;
import java.net.*;
import javax.sound.midi.*;

public class MidiPlayer {
	
	private Sequencer sequencer;
	private String filename;
	private boolean bgm;
	
	public MidiPlayer(String file, boolean b)
        {
		filename = file;
		bgm = b;
	}
	
	public void start()
        {
            try
            {
		Sequence sequence = MidiSystem.getSequence(getClass().getResource(filename));
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(sequence);
		if(bgm == true)
                {
                    sequencer.setLoopCount(999);	
		}
		sequencer.start();
            }
            catch(MalformedURLException e)
            {
            
            }
            catch(IOException e)
            {
            
            }
	    catch(MidiUnavailableException | InvalidMidiDataException e)
            {
            
            }
	}

	public void stop()
        {
            sequencer.stop();
	}
}