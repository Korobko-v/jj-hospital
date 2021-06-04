package ru.levelp.hospital.exception;

public class ServerException extends Exception{
    private ServerErrorCode serverErrorCode;

    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }

    public ServerErrorCode getServerErrorCode() {
        return serverErrorCode;
    }

}
