package com.pawscode.scalajstapir

import io.circe.generic.JsonCodec

@JsonCodec case class Account(owner: String, amount: BigDecimal)



