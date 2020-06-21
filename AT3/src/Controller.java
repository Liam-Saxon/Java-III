import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller
{
    private static MediaPlayer Player;
    boolean pause = false;
    Song song = new Song();
    LinkedList<Song> temp = new LinkedList<Song>();
    LinkedList<Song> linkedlist  = new LinkedList<Song>();
    ArrayList<Song> list = new ArrayList<Song>();
    MergeSort merge = new MergeSort();

    @FXML
    public ListView lstView;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPause;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private Button btnResume;

    //close method
    private void Close()
    {
        System.exit(0);
    }
    //show Linked list
    private void showLinkedList()
    {
        for (Song mysong : linkedlist)
       {
           lstView.getItems().add(mysong.getSongName());
       }
    }
    private void mediaPlayer()
    {
        for(Song findsong : linkedlist)
        {
            if(lstView.getSelectionModel().getSelectedItem() == findsong.getSongName())
            {
                String uriString = new File(findsong.getSongURL()).toURI().toString();
                Player = new MediaPlayer( new Media(uriString));
                Player.play();
            }
        }
    }
    //add songs
    public void setMenuItemOpen(javafx.event.ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null)
        {
            linkedlist.clear();
            //create song
            Song songs = new Song();
            //add song name and url to Song Object
            songs.setSongName(selectedFile.getName());
            songs.setSongURL(selectedFile.getAbsolutePath());
            //add to temp Linked list
            temp.add(songs);
            //linked list to array so i can merge sort
            Song[] array = temp.toArray(new Song[temp.size()]);
            //merge sort the array
            merge.mergeSort(array, 0, array.length - 1);
            //put the sorted array into the linked list
            for (int i = 0; i < array.length; i++)
            {
                linkedlist.addLast((array[i]));
            }
            //clear list view
            lstView.getItems().clear();
            //show linked list
            showLinkedList();

        }
        else{
            System.out.println("FIle not valid");
        }

    }

    public void setMenuItemClose(javafx.event.ActionEvent actionEvent)
    {
        Close();
    }
    @FXML
    public void btnSearch(javafx.event.ActionEvent actionEvent)
    {
        //create a new dummy song object
        Song songsTmp = new Song();
        //assign the text from the text field as the dummy name
        songsTmp.setSongName(txtSearch.getText());
        //compare all the names in the actual list with the dummy names
        Comparator<Song> c = Comparator.comparing(Song::getSongName);
        //binary search the linked list with the object type of songs
        int index = Collections.binarySearch(linkedlist, songsTmp, c);
        //select the searched song.
        lstView.getSelectionModel().select(index);
    }
    @FXML
    public void btnPlay(javafx.event.ActionEvent actionEvent)
    {
        mediaPlayer();
    }

    public void btnResume(javafx.event.ActionEvent actionEvent)
    {
        Player.play();
    }
    public void btnPause(javafx.event.ActionEvent actionEvent)
    {
        Player.pause();
    }
    @Test
    public void testEquals()
    {
        Song testSongs = new Song();
        song.setSongName("Starman");

        Song testSongs2 = new Song();
        song.setSongName("The Man who Sold The World");

        assertThat (testSongs.getSongName(), !equals(testSongs2.getSongName()));
    }

}
