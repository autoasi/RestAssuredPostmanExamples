package com.rest.pojo.nested;

public class WorkspaceRoot {
    Workspace workspace;

    // Jackson needs the default constructor for the de-serialization
    public WorkspaceRoot(){
    }

    public WorkspaceRoot(Workspace workspace){
        this.workspace = workspace;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}

