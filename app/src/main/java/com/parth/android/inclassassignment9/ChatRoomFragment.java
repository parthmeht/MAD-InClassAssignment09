package com.parth.android.inclassassignment9;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatRoomListener} interface
 * to handle interaction events.
 */
public class ChatRoomFragment extends Fragment {

    private ChatRoomListener mListener;
    private ImageButton signOut,addImage,sendMessage;
    private TextView displayName;
    private User user;

    public ChatRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);
        signOut = view.findViewById(R.id.signOutButton);
        displayName = view.findViewById(R.id.textViewDisplayName);
        addImage = view.findViewById(R.id.addImageButton);
        sendMessage = view.findViewById(R.id.sendButton);
        if (getArguments()!=null){
            user = (User) getArguments().getSerializable(MainActivity.USER);
            displayName.setText(user.getDisplayName());
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.signOut();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ChatRoomListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChatRoomListener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ChatRoomListener {
        void signOut();
    }
}
