import React, {Component} from 'react';
import HomepageComponent from './HomepageComponent';

class HomepageContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      affiliationsCount: null,
      computerSetsCount: null,
      hardwareCount: null,
      softwareCount: null,
      isWide: null,
    };
  }

  componentDidMount() {
    fetch('/api/statistics')
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          loading: false,
          error: false,
          ...response,
        })
      })
      .catch((response) => {
        this.setState({
          loading: false,
          error: true,
        });
      })
  }

  handleLayout = ({nativeEvent}) => {
    const {width} = nativeEvent.layout;
    this.setState({
      isWide: width > 650,
    });
  };

  render() {
    return (
      <HomepageComponent
        goToAffiliations={() => this.props.push('AffiliationsList')}
        goToComputerSets={() => this.props.push('ComputerSetsList')}
        goToHardware={() => this.props.push('HardwareList')}
        goToSoftware={() => this.props.push('SoftwareList')}
        handleLayout={this.handleLayout}
        {...this.state}
      />
    );
  }
}

export default HomepageContainer;
