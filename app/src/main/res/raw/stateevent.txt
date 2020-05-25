package com.hamidjonhamidov.cvforkhamidjon.util

interface StateEvent<ResponsibleJobEvent, DestinationViewEvent>{
    val responsibleJob: ResponsibleJobEvent
    val destinationView: DestinationViewEvent
}