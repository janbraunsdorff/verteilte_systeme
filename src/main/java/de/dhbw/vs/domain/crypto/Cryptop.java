package de.dhbw.vs.domain.crypto;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.*;

public class Cryptop {

    private final KeyPair keys;

    public Cryptop(KeyPair keys) {
        this.keys = keys;
    }

    public Cryptop() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        this.keys = kpg.genKeyPair();
    }

    public byte[] sign(String msg) throws Exception {
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(keys.getPrivate());
        sig.update(msg.getBytes(StandardCharsets.UTF_8));
        return sig.sign();
    }

    public boolean validate(String msg, byte[] signature) throws Exception{
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initVerify(keys.getPublic());
        sig.update(msg.getBytes(StandardCharsets.UTF_8));

        return sig.verify(signature);
    }

    public KeyPair getKeys() {
        return keys;
    }
}
