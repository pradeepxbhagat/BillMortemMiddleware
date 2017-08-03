package billmortem.middleware;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ServiceError {

    private Error error;

    public ServiceError(int code, String description) {
        this.error = new Error(code,description);
    }

    private class Error {
        private int code;
        private String description;

        public Error(int code, String description) {

            this.code = code;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, ServiceError.class);
    }
}
