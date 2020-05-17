package com.hamidjonhamidov.cvforkhamidjon.util

interface StateEvent<ResponsibleJob, DestinationView>{
    val responsibleJob: ResponsibleJob
    val destinationView: DestinationView
}