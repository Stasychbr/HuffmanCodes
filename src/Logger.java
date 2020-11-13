public class Logger {
    static Errors error = null;
    static public void setError(Errors error) {
        Logger.error = error;
    }
    static boolean isError() {
        return error != null;
    }
    static void printError() {
        if (error != null) {
            switch (error) {
                case CfgNotFound:
                    System.err.println("Can't open the config file");
                    break;
                case CfgFormat:
                    System.err.println("Wrong format of config file");
                    break;
                case FilesError:
                    System.err.println("Troubles with input or output file");
                    break;
                case ReaderError:
                    System.err.println("Error during input reading");
                    break;
                case WriterError:
                    System.err.println("Error during output writing");
                    break;
                case ClosingError:
                    System.err.println("Error during files closing");
                    break;
                case DecoderError:
                    System.err.println("Error during decoding");
                    break;
            }
        }
    }
}
