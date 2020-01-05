import React, {Component} from 'react';
import HardwareDetailsComponent from './HardwareDetailsComponent';

class HardwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      dictionaryID: '',
      affiliationID: '',
      computerSetID: '',
      loading: true,
      error: false,
    };
  }

  componentDidMount() {
    if (this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  addCall = () => {
    fetch('http://localhost:8080/api/hardware', {
      method: 'POST',
      body: JSON.stringify({
        "name": this.state.name,
        "dictionaryID": this.state.dictionaryID,
        "affiliationID": this.state.affiliationID,
        "computerSetID": this.state.computerSetID
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
        .then((responseJson) => {
          console.log(responseJson);
          return responseJson;
        })
        .catch((error) => {
          console.error(error);
        });
  };

  onSubmit = () => this.props.history.goBack();
  onReject = () => this.props.history.goBack();

  render() {
    return (
        <HardwareDetailsComponent
            onSubmit={this.onSubmit}
            onReject={this.onReject}
        />
    );
  }
}

export default HardwareDetailsContainer;
