package com.example.intern_food.Model;

public class Branch {
    private String branchId;
    private String name;
    private String blockId;

    public Branch() {

    }

    public Branch(String branchId, String name, String blockId) {
        this.branchId = branchId;
        this.name = name;
        this.blockId = blockId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
}
