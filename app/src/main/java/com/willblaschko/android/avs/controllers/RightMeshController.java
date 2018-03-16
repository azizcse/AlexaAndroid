package com.willblaschko.android.avs.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.left.rightmesh.android.AndroidMeshManager;
import io.left.rightmesh.android.MeshService;
import io.left.rightmesh.id.MeshID;
import io.left.rightmesh.mesh.MeshManager;
import io.left.rightmesh.mesh.MeshManager.RightMeshEvent;
import io.left.rightmesh.mesh.MeshStateListener;
import io.left.rightmesh.util.RightMeshException;
import io.reactivex.functions.Consumer;

import com.willblaschko.android.avs.R;
import com.willblaschko.android.avs.activity.IActivity;
import com.willblaschko.android.avs.models.User;
import com.willblaschko.android.avs.services.AvsService;

import static io.left.rightmesh.mesh.MeshManager.DATA_RECEIVED;
import static io.left.rightmesh.mesh.MeshManager.PEER_CHANGED;

/**
 * All RightMesh logic abstracted into one class to keep it separate from Android logic.
 */
public class RightMeshController implements MeshStateListener {
    // Port to bind app to.
    private static final int MESH_PORT = 45623;

    // MeshManager instance - interface to the mesh network.
    private AndroidMeshManager meshManager = null;

    // Set to keep track of peers connected to the mesh.
    private HashMap<MeshID, User> users = new HashMap<>();
    private User user = null;

    // Database interface.

    // Link to current activity.
    private IActivity callback = null;
    //reference to service
    private AvsService meshIMService;


    public RightMeshController(User user) {
        this.user = user;

        this.meshIMService  = meshIMService;

    }

    public void setCallback(IActivity callback) {
        this.callback = callback;
        //updateInterface();
    }

    /**
     * Returns a list of online users.
     * @return online users
     */
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    /**
     * Sends a simple text message to another user.
     * @param recipient recipient of the message
     * @param message contents of the message
     */
    public void sendTextMessage(User recipient, String message) {
       /* Message messageObject = new Message(user, recipient, message, true);
        try {
            meshManager.sendDataReliable(recipient.getMeshId(), MESH_PORT,
                    createMessagePayloadFromMessage(messageObject));

            dao.insertMessages(messageObject);
            updateInterface();
        } catch (RightMeshException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Get a {@link AndroidMeshManager} instance, starting RightMesh if it isn't already running.
     *
     * @param context service context to bind to
     */
    public void connect(Context context) {
        String ssid = context.getString(R.string.network_name);
        meshManager = AndroidMeshManager.getInstance(context, RightMeshController.this, ssid);
    }

    /**
     * Close the RightMesh connection, stopping the service if no other apps are running.
     */
    public void disconnect() {
        try {
            meshManager.stop();
        } catch (MeshService.ServiceDisconnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by the {@link MeshService} when the mesh state changes. Initializes mesh connection
     * on first call.
     *
     * @param uuid  our own user id on first detecting
     * @param state state which indicates SUCCESS or an error code
     */
    @Override
    public void meshStateChanged(MeshID uuid, int state) {
        if (state == MeshStateListener.SUCCESS) {
            //user.setMeshId(uuid);
            //user.save();
            try {
                // Binds this app to MESH_PORT.
                // This app will now receive all events generated on that port.
                meshManager.bind(MESH_PORT);
                // Subscribes handlers to receive events from the mesh.
                meshManager.on(DATA_RECEIVED, new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        handleDataReceived((MeshManager.RightMeshEvent) o);
                    }
                });
                meshManager.on(PEER_CHANGED, new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        handlePeerChanged((MeshManager.RightMeshEvent) o);
                    }
                });

            } catch (RightMeshException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDataReceived(RightMeshEvent e) {

    }

    private void handlePeerChanged(RightMeshEvent e) {
       /* // Update peer list.
        PeerChangedEvent event = (PeerChangedEvent) e;

        // Ignore ourselves.
        if (event.peerUuid == user.getMeshId()) {
            return;
        }

        if (event.state == ADDED) {
            // Send our information to a new or rejoining peer.
            byte[] message = createPeerUpdatePayloadFromUser(user);
            try {
                meshManager.sendDataReliable(event.peerUuid, MESH_PORT, message);
            } catch (RightMeshException rme) {
                rme.printStackTrace();
            }
        } else if (event.state == REMOVED) {
            users.remove(event.peerUuid);
            updateInterface();
        }*/
    }


    /*private void updateInterface() {
        try {
            if (callback != null) {
                callback.updateInterface();
            }
        } catch (RemoteException | NullPointerException ignored) {
            // Just keep swimming.
        }
    }*/

    /**
     * Handles incoming data events from the mesh - toasts the contents of the data.
     *
     * @param e event object from mesh
     */
    /*private void handleDataReceived(RightMeshEvent e) {
        DataReceivedEvent event = (DataReceivedEvent) e;

        try {
            MeshIMMessage messageWrapper = MeshIMMessage.parseFrom(event.data);
            MeshID peerId = event.peerUuid;

            MessageType messageType = messageWrapper.getMessageType();
            if (messageType == PEER_UPDATE) {
                PeerUpdate peerUpdate = messageWrapper.getPeerUpdate();

                // Initialize peer with info from update packet.
                User peer = new User(peerUpdate.getUserName(), peerUpdate.getAvatarId(), peerId);

                // Create or update user in database.
                MeshIDTuple dietPeer = dao.fetchMeshIdTupleByMeshId(peerId);
                if (dietPeer == null) {
                    dao.insertUsers(peer);

                    // Fetch the user's id after it is initialized.
                    dietPeer = dao.fetchMeshIdTupleByMeshId(peerId);
                    peer.id = dietPeer.id;
                } else {
                    peer.id = dietPeer.id;
                    dao.updateUsers(peer);
                }

                // Store user in list of online users.
                users.put(peerId, peer);
                updateInterface();
            } else if (messageType == MESSAGE) {
                MeshIMMessages.Message protoMessage = messageWrapper.getMessage();
                User sender = users.get(peerId);
                Message message = new Message(sender, user, protoMessage.getMessage(), false);
                dao.insertMessages(message);
                meshIMService.sendNotification(sender,message);
                updateInterface();
            }
        } catch (InvalidProtocolBufferException ignored) { *//* Ignore malformed messages. *//* }
    }
*/
    /**
     * Handles peer update events from the mesh - maintains a list of peers and updates the display.
     *
     * @param e event object from mesh
     */
    /*private void handlePeerChanged(RightMeshEvent e) {
        // Update peer list.
        PeerChangedEvent event = (PeerChangedEvent) e;

        // Ignore ourselves.
        if (event.peerUuid == user.getMeshId()) {
            return;
        }

        if (event.state == ADDED) {
            // Send our information to a new or rejoining peer.
            byte[] message = createPeerUpdatePayloadFromUser(user);
            try {
                meshManager.sendDataReliable(event.peerUuid, MESH_PORT, message);
            } catch (RightMeshException rme) {
                rme.printStackTrace();
            }
        } else if (event.state == REMOVED) {
            users.remove(event.peerUuid);
            updateInterface();
        }
    }*/

    /**
     * Creates a byte array representing a {@link User}, to be broadcast over the mesh.
     * @param user user to be represented in bytes
     * @return payload to be broadcast
     */
    /*private byte[] createPeerUpdatePayloadFromUser(User user) {
        PeerUpdate peerUpdate = PeerUpdate.newBuilder()
                .setUserName(user.getUsername())
                .setAvatarId(user.getAvatar())
                .build();

        MeshIMMessage message = MeshIMMessage.newBuilder()
                .setMessageType(PEER_UPDATE)
                .setPeerUpdate(peerUpdate)
                .build();

        return message.toByteArray();
    }

    private byte[] createMessagePayloadFromMessage(Message message) {
        MeshIMMessages.Message protoMsg = MeshIMMessages.Message.newBuilder()
                .setMessage(message.getMessage())
                .setTime(message.getDateAsTimestamp())
                .build();

        MeshIMMessage payload = MeshIMMessage.newBuilder()
                .setMessageType(MESSAGE)
                .setMessage(protoMsg)
                .build();

        return payload.toByteArray();
    }*/

    /**
     * Displays Rightmesh setting page.
     */
    public void showRightMeshSettings() {
        try {
            meshManager.showSettingsActivity();
        } catch (RightMeshException e) {
            e.printStackTrace();
        }
    }
}