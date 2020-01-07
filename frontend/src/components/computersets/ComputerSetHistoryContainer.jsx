import React, {Component} from 'react';
import ComputerSetHistoryComponent from './ComputerSetHistoryComponent';
import request from "../../APIClient";

class ComputerSetHistoryContainer extends Component {
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
        this.fetchData();
    }

    fetchData = () => {
        request('/api/computer-sets/' + this.props.id.toString + '/history/' + this.props.mode.toString)
            .then((response) => response.json())
            .then((response) => {
                this.setState({
                    loading: false,
                    ...response,
                });
            })
            .catch(() => {
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
                name: 'computerSetInventoryNumber',
                label: 'Numer inwentarzowy',
            },
            {
                name: 'affiliationName',
                label: 'Przynależy do',
            },
        ];
        const columnsSoftwareMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
            {
                name: 'computerSetInventoryNumber',
                label: 'Numer inwentarzowy',
            },
            {
                name: 'affiliationName',
                label: 'Przynależy do',
            },
        ];
        const columnsHardwareMode = [
            {
                name: 'name',
                label: 'Nazwa',
            },
            {
                name: 'computerSetInventoryNumber',
                label: 'Numer inwentarzowy',
            },
            {
                name: 'affiliationName',
                label: 'Przynależy do',
            },
        ];

        let actualColumns;
        if (this.props.mode === 'affiliations') {
            actualColumns = columnsAffiliationsMode
        }
        if (this.props.mode === 'computersets') {
            actualColumns = columnsHardwareMode
        }
        if (this.props.mode === 'software') {
            actualColumns = columnsSoftwareMode
        }

        return (
            <ComputerSetHistoryComponent
                onFetchData={this.fetchData}
                columns={actualColumns}
                {...this.state}
            />
        );
    }
}

export default ComputerSetHistoryContainer;
