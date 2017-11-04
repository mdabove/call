package almundo.comparator;

import java.util.Comparator;

import almundo.model.CallCenterEmployee;

public class ComparatorComponent {

    public Comparator<CallCenterEmployee> getEmployeesComparator() {
        return new Comparator<CallCenterEmployee>() {
            @Override
            public int compare(CallCenterEmployee c1, CallCenterEmployee c2) {
                return (int) (c1.getEmployeeType().getValue() - c2.getEmployeeType().getValue());
            }
        };
    }

}
