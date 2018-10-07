package ru.vlabum.chatone.model;

public enum  PacketType {
    NONE,
    PING,
    RESULT,

    REGISTRY,
    LOGOUT,
    LOGIN,

    MESSAGE,
    BROADCAST,
    UNICAST
}
