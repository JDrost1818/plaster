package github.jdrost1818.plaster.method;

import github.jdrost1818.plaster.domain.FileInformation;

public interface PlasterMethod<T> {

    void perform(T service, FileInformation information);

}
