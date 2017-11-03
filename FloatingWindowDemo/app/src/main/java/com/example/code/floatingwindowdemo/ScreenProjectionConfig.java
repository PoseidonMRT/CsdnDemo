package com.example.code.floatingwindowdemo;

/**
 * Global variable for screen projection function
 *
 * @author tuozhaobing
 * @date 2017.10.13
 */

public class ScreenProjectionConfig {
    public static final String PERMISSION_RESULT_CODE_TAG = "SCREEN_PUSH_PERMISSION_RESULT_CODE";
    public static final String PERMISSION_REQUEST_ACTION = "android.intent.action.screen.projection";
    public static final int SCREEN_PROJECTION_VIDEO_BITRATE =640000*8;

    public static final String SCREEN_PROJECTION_REQUEST_DATA = "screen_projection_data";
    //student disconnected action
    public static final String STUDENT_DISCONNECTED_ACTION = "student_disconnected_action";
    //teacher disconnected action
    public static final String TEACHER_DISCONNECTED_ACTION = "teacher_disconnected_action";
    //screen projection channel state(server to client)
    public static final String OPEN_SCREEN_PROJECTION_ACTION = "open_screen_projection_channel";
    public static final String CLOSE_SCREEN_PROJECTION_ACTION = "close_screen_projection_channel";
    //screen projection data state(server to client)
    public static final String ACTIVE_SCREEN_PROJECTION_DATA_ACTION = "start_screen_projection";
    public static final String INACTIVE_SCREEN_PROJECTION_DATA_ACTION = "end_screen_projection";

    public static final String SCREEN_SHOT_FILE_NAME = "screen_shot_tmp.png";

    public static final String CLASSROOMP2P = "classroomp2p";
    public static final String P2PCOMMAND = "p2p_command";
    public static final String COMMAND = "command";
    public static final String STREAM_CHANNEL_ID = "stream_channel_id";
    public static final String THUMBNAIL_SUFFIX = "thumbnail_suffix";
    public static final String THUMBNAIL_BUFFER_ID = "thumbnail_buffer_id";
    public static final String STOP_ERROR_CODE = "code";

    public static final String DATA_BUFFER_ID = "data_buffer_id";
    public static final String VIDEO_WIDTH = "width";
    public static final String VIDEO_HEIGHT = "height";

    public static final String CLASS_RECORD_ID = "class_record_id";

    //connect or disconnect from server (client to server )
    public static final String REGISTER_SCREEN_PROJECTION_COMMAND = "register_screen_push" ;
    public static final String REGISTER_SCREEN_PROJECTION_P2P_COMMAND = "register_screen_push" ;

    public static final String UNREGISTER_SCREEN_PROJECTION_COMMAND = "unregister_screen_push";
    public static final String UNREGISTER_SCREEN_PROJECTION_P2P_COMMAND = "unregister_screen_push";

    //push screen video data to server(client to server)
    public static final String PUSH_SCREEN_PROJECTION_DATA_COMMAND = "screen_push_data";
    public static final String PUSH_SCREEN_PROJECTION_DATA_P2P_COMMAND = "screen_push_data";

    //start screen video push command (client to server)
    public static final String BEGIN_PUSH_SCREEN_PROJECTION_DATA_COMMAND = "start_screen_push";
    public static final String BEGIN_PUSH_SCREEN_PROJECTION_DATA_P2P_COMMAND = "start_screen_push";

    //stop screen video push command (client to server)
    public static final String END_PUSH_SCREEN_PROJECTION_DATA_COMMAND = "stop_screen_push";
    public static final String END_PUSH_SCREEN_PROJECTION_DATA_P2P_COMMAND = "stop_screen_push";
}
