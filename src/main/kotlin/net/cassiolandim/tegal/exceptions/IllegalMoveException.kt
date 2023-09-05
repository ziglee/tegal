package net.cassiolandim.tegal.exceptions

class IllegalMoveException(
    override val message: String
): IllegalStateException(message)