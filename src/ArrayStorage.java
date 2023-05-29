import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int index = -1;

    void clear() {
        index = -1;
    }

    void save(Resume r) {
        index++;
        storage[index] = r;

    }

    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i <= index; i++) {
            if (storage[i].toString().equals(uuid)) {
                resume = storage[i];
                break;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        int deletedIndex = -1;
        for (int i = 0; i <= index; i++) {
            if (storage[i].toString().equals(uuid)) {
                deletedIndex = i;
                break;
            }
        }
        if (deletedIndex != -1) {
            for (int i = deletedIndex + 1; i <= index; i++) {
                storage[i - 1] = storage[i];
            }
            index--;
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, index + 1);
    }

    int size() {
        return index + 1;
    }
}
