### **1. Image File Breakdown (`train-images-idx3-ubyte` and `t10k-images-idx3-ubyte`)**

Each image file contains:

- **Magic number** (4 bytes)
- **Number of images** (4 bytes)
- **Number of rows** (4 bytes)
- **Number of columns** (4 bytes)
- **Image data**: 28 × 28 = **784 bytes per image**

#### **Total Breakdown:**

- **Header**: 4 + 4 + 4 + 4 = **16 bytes**
- **Image Data**:
    - Train: `60,000 × 784 = 47,040,000 bytes`
    - Test: `10,000 × 784 = 7,840,000 bytes`
- **Total File Size**:
    - Train: `16 + 47,040,000 = 47,040,016 bytes`
    - Test: `16 + 7,840,000 = 7,840,016 bytes`

---

### **2. Label File Breakdown (`train-labels-idx1-ubyte` and `t10k-labels-idx1-ubyte`)**

Each label file contains:

- **Magic number** (4 bytes)
- **Number of labels** (4 bytes)
- **Label data**: 1 byte per label

#### **Total Breakdown:**

- **Header**: 4 + 4 = **8 bytes**
- **Label Data**:
    - Train: `60,000 × 1 = 60,000 bytes`
    - Test: `10,000 × 1 = 10,000 bytes`
- **Total File Size**:
    - Train: `8 + 60,000 = 60,008 bytes`
    - Test: `8 + 10,000 = 10,008 bytes`

---

### **Final Summary:**

| File                      | Header (bytes) | Data (bytes) | Total Size (bytes) |
| ------------------------- | -------------- | ------------ | ------------------ |
| `train-images-idx3-ubyte` | 16             | 47,040,000   | 47,040,016         |
| `t10k-images-idx3-ubyte`  | 16             | 7,840,000    | 7,840,016          |
| `train-labels-idx1-ubyte` | 8              | 60,000       | 60,008             |
| `t10k-labels-idx1-ubyte`  | 8              | 10,000       | 10,008             |