package slh.services.usermodel;

import java.util.UUID;

public final class UserModelRequest
{
    UserModelRequestItem[] contentItems;

    public UserModelRequest(String content)
    {
        contentItems = new UserModelRequestItem[] { new UserModelRequestItem(content) };
    }

    public static final class UserModelRequestItem
    {
        String userid      = UUID.randomUUID().toString();
        String id          = UUID.randomUUID().toString();
        String sourceid    = "freetext";
        String contenttype = "text/plain";
        String language    = "en";
        String content;

        public UserModelRequestItem(String content)
        {
            this.content = content;
        }
    }
}
