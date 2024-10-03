package ku.cs.service;

import javafx.application.Platform;
import ku.cs.controller.LoadingController;

public class LoadService {

    private static Loader loader;

    static public class Loader {
        private LoadingController controller;
        private double progress;
        private ProgressSetter progressSetter;

        private Loader(ProgressSetter firstProgressSetter) {
            progress = 0;
            progressSetter = firstProgressSetter;

        }

        private void setController(LoadingController controller) {
            this.controller = controller;
        }
        public LoadingController getController() {
            return controller;
        }

        public void updateBar() {
            Platform.runLater(() -> controller.progressBar.setProgress(this.progress + this.progressSetter.getPercentage()));
        }
        public void addProgressSetter(ProgressSetter progressSetter) {
            this.close();
            this.progressSetter = progressSetter;
        }
        public void close() {
            this.progressSetter.close();
            progress += this.progressSetter.getPercentage();
        }
    }

    public static void init(LoadingController controller, ProgressSetter progressSetter) {
        loader = new Loader(progressSetter);
        loader.setController(controller);
    }

    public static Loader getLoader() {
        return loader;
    }

}
