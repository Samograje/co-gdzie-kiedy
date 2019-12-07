import React, {Component} from 'react';
import HardwareListComponent from './HardwareListComponent';

class HardwareListContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false
    };
  }

  componentDidMount() {
    fetch('/api/hardware')
        .then((response) => response.json())
        .then((response) => {
          this.setState({
            loading: false,
            error: false,
            ...response
          })
        })
        .catch((response) => {
          this.setState({
            loading: false,
            error: true
          });
        })
  }


  render() {
    return (
      <HardwareListComponent
          {...this.state}
      />
    );
  }
}

export default HardwareListContainer;
