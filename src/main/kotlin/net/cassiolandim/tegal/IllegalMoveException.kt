package net.cassiolandim.tegal

class IllegalMoveException(
    override val message: String
): IllegalStateException(message)