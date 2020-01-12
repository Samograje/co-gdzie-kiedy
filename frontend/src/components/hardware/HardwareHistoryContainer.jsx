import React, {Component} from 'react';
import HardwareHistoryComponent from './HardwareHistoryComponent';
import request from "../../APIClient";

class HardwareHistoryContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            error: false,
            items: [],
            totalElements: null,
        };
    }

    componentDidMount() {
        this._isMounted = true;
        this.fetchData();
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    fetchData = () => {
        request(`/api/hardware/${this.props.id}/${this.props.mode}-history`)
            .then((response) => response.json())
            .then((response) => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loading: false,
                    ...response,
                });
            })
            .catch(() => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loading: false,
                    error: true,
                });
            })
    };

    render() {

        const columnsAffiliationsMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
            {
                name: 'validFrom',
                label: 'Ważne od',
            },
            {
                name: 'validTo',
                label: 'Ważne do',
            },
        ];
        const columnsComputerSetsMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
            {
                name: 'inventoryNumber',
                label: 'Numer inwentarzowy',
            },
            {
                name: 'validFrom',
                label: 'Ważne od',
            },
            {
                name: 'validTo',
                label: 'Ważne do',
            },
        ];

        let actualColumns;
        if (this.props.mode === 'affiliations') {
            actualColumns = columnsAffiliationsMode
        }
        if (this.props.mode === 'computer-sets') {
            actualColumns = columnsComputerSetsMode
        }

        return (
            <HardwareHistoryComponent
                onFetchData={this.fetchData}
                columns={actualColumns}
                {...this.state}
            />
        );
    }
}

export default HardwareHistoryContainer;
