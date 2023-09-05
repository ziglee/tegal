package net.cassiolandim.tegal.exceptions

class EntityNotFoundException(
    override val message: String
): IllegalArgumentException(message)