import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.*;

    public class JukeBox
    {

	private JukeBox.BackgroundSound bs;
	private HashMap availableClips;
	private HashMap playingClips;
	private int nextClip = 0;
	private boolean debug;
	
	public JukeBox()
        {
            availableClips = new HashMap();
            playingClips = new HashMap();
	}
	
	public void setDebug(boolean b)
        {
            debug = b;
	}
       
	public boolean loadClip (String resourcePath, String soundName, int howMany)
        {
            for(int x = 0; x < howMany; x++)
            {
		try
                {
                    InputStream is = getClass().getResourceAsStream(resourcePath);
                    if(is == null)
                    {
                        System.out.println("");
			return false;
                    }
                    boolean loaded = loadClip(is, soundName);
                    if (!(loaded))
                    {
                        System.out.println("Can't load sound");
                        return false;
                    }
		}
		catch(Exception e)
                {
                    return false;
		}
            }
            return true;
	}
	
	private boolean loadClip (InputStream is, String name)
        {
            name = trimExtension(name);
            if (!availableClips.containsKey(name))
            {          
                LinkedList<HashMap> list2 = new LinkedList();
                availableClips.put(name, list2);
            }
            List<JukeBox.Sound> list =(List<JukeBox.Sound>)availableClips.get(name);
            AudioInputStream audioInputStream = null;
            try
            {
                audioInputStream = AudioSystem.getAudioInputStream(is);
            }
            catch(UnsupportedAudioFileException | IOException e)
            {
                System.out.println("Cant get audio input");
            }
		
            if(audioInputStream == null)
            {
                return false;
            }
		
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            JukeBox.Sound clip = null;
            try 
            {
                clip = new JukeBox.Sound((Clip) AudioSystem.getLine(info),audioInputStream,name,nextClip++,this);
                    list.add(clip);
                    audioInputStream.close();
		}
		catch(LineUnavailableException | IOException e)
                {
                    return false;
		}
		
		if(clip == null)
                {
                    return false;
		}
		return true;
	}
	
	private static String trimExtension(String name)
        {
            int last = name.lastIndexOf(".");
            if(last == 0)
            {
                throw new IllegalArgumentException("can't start a name with a dot");
            }
            if(last > -1)
            {
                return name.substring(0, last);
            }
            else
            {
                return name;
            }
	}
	
	public int playClip(String name)
        {
            return playClip(name, 1);
	}
       
	public synchronized int playClip(String name, int numberOfLoops)
        {
            name = trimExtension(name);
            if(!availableClips.containsKey(name))
            {
                return -1;
            }
            List<JukeBox.Sound> clips = (List<JukeBox.Sound>) availableClips.get(name);
            print("gonna playClip " + name + "" + " from the " + clips.size() + " available copies");
            print(Clip.LOOP_CONTINUOUSLY + "");
            if(clips.isEmpty())
            {
                return -1;
            }
            JukeBox.Sound clip = (JukeBox.Sound) clips.remove(0);
            playingClips.put((clip.getID()), clip);
            if(numberOfLoops == 1)
            {
                clip.play();
            }
            else
            {
                print("continuous looping call");
                clip.loop(numberOfLoops);
            }
            return clip.getID();		
	}
	
	public static int getSoundLength(InputStream is)
        {
            AudioInputStream ais = null;
            try
            {
                ais = AudioSystem.getAudioInputStream(is);
            } 
            catch(UnsupportedAudioFileException | IOException e)
            {
		
            }
            if(ais == null)
            {
                return -1;
            }
            long frames = ais.getFrameLength();
            AudioFormat	format = ais.getFormat();
            float framesPerSecond = format.getFrameRate();
            int seconds = (int) (frames / framesPerSecond);
            return seconds;
	}
	
	public synchronized JukeBox.BackgroundSound playBackground(InputStream is)
        {
            if(bs != null)
            {
                bs.stopBackgroundSound(false);
            }
            AudioInputStream ais = null;
            try
            {
		ais = AudioSystem.getAudioInputStream(is);
            } 
            catch(UnsupportedAudioFileException | IOException e)
            {
		
            }
            if(ais == null)
            {
		return null;
            }
            AudioFormat	format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(
            SourceDataLine.class, format);
            SourceDataLine background = null;
            try
            {
                background = (SourceDataLine) AudioSystem.getLine(info);
            } 
            catch(LineUnavailableException e)
            {
            
            }
            try
            {
                background.open();
            } 
            catch(LineUnavailableException e)
            {
            
            }
            background.start();
            bs = new JukeBox.BackgroundSound(ais, background);
            return bs;
	}
        
	public void stopCurrentBackgroundSound(boolean noMoreSounds)
        {
            if(bs != null)
            {
                bs.stopBackgroundSound(noMoreSounds);
            }
	}
       
	public synchronized void makeAvailable(JukeBox.Sound sound)
        {
            if (availableClips.containsKey(sound.getName()))
            {
                playingClips.remove(new Integer(sound.getID()));
                ((List<Object>)availableClips.get(sound.getName())).add(sound);
            }
	}
	
	public synchronized void stopClip(int id)
        {
            Integer ID = new Integer(id);
            if(playingClips.containsKey(ID))
            {
                JukeBox.Sound s = (JukeBox.Sound) playingClips.get(ID);
		s.stop();
            }
	}
	
        public synchronized void stopAllClips()
        {
            Iterator it = playingClips.keySet().iterator();
            while (it.hasNext()) {
            JukeBox.Sound s = (JukeBox.Sound) playingClips.get((Integer)it.next());
            s.stop();
        }
    }
    
        private void print(String message)
        {
            if(debug)
            {
		print(message);
            }
	}

	public synchronized void close()
        {
            stopAllClips();
            stopCurrentBackgroundSound(true);
	}

	public class Sound implements LineListener
        {
            private Clip m_clip;
            private String name;
            private int id;
            private boolean looping = false;
            JukeBox jukeBox;
            public Sound (Clip clip, AudioInputStream ais,String name,int id,JukeBox jukeBox) throws LineUnavailableException, IOException 
            {
		this.name = name;
		this.id = id;
		m_clip = clip;
		m_clip.addLineListener(this);
		m_clip.open(ais);
		this.jukeBox = jukeBox;
            }
            
            public String getName()
            {
		return name;
            }
		
            @Override
            public void update(LineEvent event)
            {
                if(event.getType().equals(LineEvent.Type.STOP))
		{
                    if(looping)
                    {
			return;
                    }
                    m_clip.stop();
                    m_clip.setFramePosition(0);
                    jukeBox.makeAvailable(this);
		}
            }
		
            public void play()
            {
		m_clip.start();
            }
            public void loop(int numberOfLoops)
            {
		m_clip.setLoopPoints(0, -1);
		looping = true;
		m_clip.loop(numberOfLoops);
            }
            public int getID()
            {
		return id;
            }
            public void stop()
            {
		looping = false;
		m_clip.stop();
            }
	}	
	
	public class BackgroundSound extends Thread
        {
            private AudioInputStream ais;
            private SourceDataLine sdl;
            private boolean done;
            private boolean kill;
            JukeBox.BackgroundSoundObserver bso;
		
            public BackgroundSound (AudioInputStream ais, SourceDataLine sdl) 
            {
		this(ais, sdl, null);
            }
		
            public BackgroundSound (AudioInputStream ais, SourceDataLine  sdl, JukeBox.BackgroundSoundObserver bso)
            {
		this.ais = ais;
		this.sdl = sdl;
		this.bso = bso;
		done = false;
		start();
            }
		
            @Override
            public void run()
            {
                boolean allBytesRead = false;
		while(!done)
                {
                    int	nBytesRead = 0;
                    byte[]	abData = new byte[2048];
                    while(nBytesRead != -1 && !done)
                    {
                        try
                        {
                            nBytesRead = ais.read(abData, 0, abData.length);
                        }
                        catch (IOException e)
                        {
                        
                        }
                        if(nBytesRead >= 0)
                        {
                            sdl.write(abData, 0, nBytesRead);
                        }
                        else
                        {
                            done = true;
                            allBytesRead = true;
                        }
                    }
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
		if(!kill && bso != null)
                {
                    bso.soundDone(this, allBytesRead);
		}
            }

            public void registerObserver(JukeBox.BackgroundSoundObserver bso)
            {
		this.bso = bso;
            }
            public void stopBackgroundSound(boolean noMoreSounds)
            {
                if(done)
                {
                    return;
		}
		done = true;
		kill = noMoreSounds;
            }
        }
	
	public interface BackgroundSoundObserver
        {
            public void soundDone(JukeBox.BackgroundSound bs, boolean allBytesRead);	
	}

}
