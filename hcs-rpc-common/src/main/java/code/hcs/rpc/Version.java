package code.hcs.rpc;

/**
 * Created by Administrator on 2016/12/28.
 */
public enum Version {

    VERSION((byte)1,16);

    byte version;
    int headerLength;

    Version(byte version, int headerLength) {
        this.version = version;
        this.headerLength = headerLength;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }
}
